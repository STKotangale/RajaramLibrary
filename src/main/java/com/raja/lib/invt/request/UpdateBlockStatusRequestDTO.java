package com.raja.lib.invt.request;

import java.util.List;

public class UpdateBlockStatusRequestDTO {
    private List<Integer> membOnlineIds;

    public List<Integer> getMembOnlineIds() {
        return membOnlineIds;
    }

    public void setMembOnlineIds(List<Integer> membOnlineIds) {
        this.membOnlineIds = membOnlineIds;
    }
}
