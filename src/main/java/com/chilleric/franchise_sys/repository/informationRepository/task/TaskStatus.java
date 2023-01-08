package com.chilleric.franchise_sys.repository.informationRepository.task;

public enum TaskStatus {
    TODO("To-do"), IN_PROGRESS("In-progress"), NEED_BE_REVIEWED("Need-be-reviewed"), DONE("Done");

    private String statusTask;

    TaskStatus(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getStatusTask() {
        return statusTask;
    }
}

