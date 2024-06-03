package com.ujnbox.cheetah.service;

public interface MaintRecordService {
    public boolean generateNewRecord(String clientName, String phoneNumber, String unit, String room, Integer buildingId, Integer adderId, Integer maintType, String maintDescription, String locationDescription);
}
