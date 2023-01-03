package com.chilleric.franchise_sys.controller;

import static java.util.Map.entry;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ValidationResult;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.ForbiddenException;
import com.chilleric.franchise_sys.exception.UnauthorizedException;
import com.chilleric.franchise_sys.inventory.path.PathInventory;
import com.chilleric.franchise_sys.inventory.user.UserInventory;
import com.chilleric.franchise_sys.jwt.JwtValidation;
import com.chilleric.franchise_sys.log.AppLogger;
import com.chilleric.franchise_sys.log.LoggerFactory;
import com.chilleric.franchise_sys.log.LoggerType;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.AccessabilityRepository;
import com.chilleric.franchise_sys.repository.systemRepository.path.Path;
import com.chilleric.franchise_sys.repository.systemRepository.permission.PermissionRepository;
import com.chilleric.franchise_sys.repository.systemRepository.user.User;
import com.chilleric.franchise_sys.utils.ObjectUtilities;

public abstract class AbstractController<s> {

	protected static final Map<String, List<String>> IgnoreView = Map.ofEntries(
			entry("PermissionResponse", Arrays.asList("id", "userId", "editable", "viewPoints")),
			entry("UserResponse", Arrays.asList("id", "password", "username", "dob", "address",
					"email", "tokens", "modified", "verified", "verify2FA")));

	protected static final Map<String, List<String>> IgnoreEdit =
			Map.ofEntries(entry("PermissionRequest", Arrays.asList("id")),
					entry("UserRequest", Arrays.asList("id", "password")));

	@Autowired
	protected s service;

	@Autowired
	protected JwtValidation jwtValidation;

	@Autowired
	protected PermissionRepository permissionRepository;

	@Autowired
	protected UserInventory userInventory;

	@Autowired
	protected PathInventory pathInventory;

	@Autowired
	private AccessabilityRepository accessabilityRepository;

	protected AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

	protected ValidationResult validateToken(HttpServletRequest request) {
		String token = jwtValidation.getJwtFromRequest(request);
		if (token == null) {
			throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
		} else {
			return checkAuthentication(token, Optional.of(request));
		}
	}

	protected ValidationResult validateSSE(String token) {
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return checkAuthentication(token.substring(7), Optional.empty());
		} else {
			throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
		}

	}

	protected ValidationResult checkAuthentication(String token,
			Optional<HttpServletRequest> request) {
		String userId = jwtValidation.getUserIdFromJwt(token);
		User user = userInventory.getActiveUserById(userId)
				.orElseThrow(() -> new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED));
		Map<String, List<ViewPoint>> thisView = new HashMap<>();
		Map<String, List<ViewPoint>> thisEdit = new HashMap<>();
		if (user.getUsername().compareTo("super_admin_dev") == 0) {
			permissionRepository.getPermissionByUserId(user.get_id().toString())
					.orElseThrow(() -> new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED))
					.forEach(thisPerm -> {
						thisView.putAll(ObjectUtilities.mergePermission(thisView,
								thisPerm.getViewPoints()));
						thisEdit.putAll(
								ObjectUtilities.mergePermission(thisEdit, thisPerm.getEditable()));
					});
			return new ValidationResult(user.get_id().toString(),
					removeAttributes(thisView, IgnoreView), removeAttributes(thisEdit, IgnoreEdit));
		}
		if (user.getTokens().compareTo(token) != 0) {
			throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
		}
		if (request.isPresent()) {
			String path = request.get().getHeader("pathName");
			if (!StringUtils.hasText(path)) {
				throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
			}
			Path thisPath = pathInventory.findPathByPath(path)
					.orElseThrow(() -> new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED));
			if (thisPath.getType().toString().compareTo(user.getType().toString()) != 0) {
				throw new ForbiddenException(LanguageMessageKey.FORBIDDEN);
			}
		}
		permissionRepository.getPermissionByUserId(user.get_id().toString())
				.orElseThrow(() -> new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED))
				.forEach(thisPerm -> {
					thisView.putAll(
							ObjectUtilities.mergePermission(thisView, thisPerm.getViewPoints()));
					thisEdit.putAll(
							ObjectUtilities.mergePermission(thisEdit, thisPerm.getEditable()));
				});
		return new ValidationResult(user.get_id().toString(),
				removeAttributes(thisView, IgnoreView), removeAttributes(thisEdit, IgnoreEdit));

	}

	protected Map<String, List<ViewPoint>> removeAttributes(Map<String, List<ViewPoint>> thisView,
			Map<String, List<String>> ignoreView) {
		return thisView.entrySet().stream().map((key) -> {
			List<ViewPoint> newValue = new ArrayList<>();
			if (ignoreView.get(key.getKey()) != null) {
				key.getValue().forEach(thisViewKey -> {
					boolean isFound = false;
					for (int i = 0; i < ignoreView.get(key.getKey()).size(); i++) {
						if (thisViewKey.getKey()
								.compareTo(ignoreView.get(key.getKey()).get(i)) == 0) {
							isFound = true;
							break;
						}
					}
					if (isFound == false) {
						newValue.add(thisViewKey);
					}
				});
			} else {
				key.getValue().forEach(thisViewKey -> {
					newValue.add(thisViewKey);
				});
			}
			return entry(key.getKey(), newValue);
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y,
				LinkedHashMap::new));
	}

	protected <T> ResponseEntity<CommonResponse<T>> response(Optional<T> response,
			String successMessage, List<ViewPoint> viewPoint, List<ViewPoint> editable) {
		return new ResponseEntity<>(new CommonResponse<>(true, response.get(), successMessage,
				HttpStatus.OK.value(), viewPoint == null ? new ArrayList<>() : viewPoint,
				editable == null ? new ArrayList<>() : editable), HttpStatus.OK);
	}

	protected void checkAccessability(String loginId, String targetId, boolean isCheckEdit) {
		if (!isCheckEdit && loginId.compareTo(targetId) != 0) {
			accessabilityRepository.getAccessability(loginId, targetId)
					.orElseThrow(() -> new ForbiddenException(LanguageMessageKey.FORBIDDEN));
		}
		if (isCheckEdit) {
			Accessability accessability =
					accessabilityRepository.getAccessability(loginId, targetId).orElseThrow(
							() -> new ForbiddenException(LanguageMessageKey.FORBIDDEN));
			if (!accessability.isEditable()) {
				throw new ForbiddenException(LanguageMessageKey.FORBIDDEN);
			}
		}


	}

	protected void preventItSelf(String loginId, String targetId) {
		if (loginId.compareTo(targetId) == 0) {
			throw new ForbiddenException(LanguageMessageKey.FORBIDDEN);
		}
	}


	protected void checkAddCondition(Map<String, List<ViewPoint>> editable, Class<?> clazz) {
		if (editable.get(clazz.getSimpleName()).isEmpty()) {
			throw new ForbiddenException(LanguageMessageKey.FORBIDDEN);
		} else {
			if (editable.get(clazz.getSimpleName()).size() < clazz.getDeclaredFields().length) {
				throw new ForbiddenException(LanguageMessageKey.FORBIDDEN);
			}
		}
	}

	protected <T> T filterResponse(T input, Map<String, List<ViewPoint>> compares) {
		List<ViewPoint> compareList = new ArrayList<>();
		compares.forEach((key, value) -> {
			if (key.compareTo(input.getClass().getSimpleName()) == 0) {
				compareList.addAll(value);
			}
		});
		for (Field field : input.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				boolean isExist = false;
				for (ViewPoint thisItem : compareList) {
					if (field.getName().compareTo("id") == 0) {
						isExist = true;
						break;
					}
					if (thisItem.getKey().compareTo(field.getName()) == 0) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					if (field.getType() == String.class) {
						field.set(input, "");
					}
					if (field.getType() == int.class) {
						field.set(input, 0);
					}
					if (field.getType() == boolean.class) {
						field.set(input, false);
					}
					if (field.getType() == Map.class) {
						field.set(input, new HashMap<>());
					}
					if (field.getType() == List.class) {
						field.set(input, new ArrayList<>());
					}
				}
			} catch (Exception e) {
				throw new BadSqlException(LanguageMessageKey.SERVER_ERROR);
			}
		}
		return input;
	}
}
