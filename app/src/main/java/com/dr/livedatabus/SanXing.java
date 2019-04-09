package com.dr.livedatabus;

/**
 * 项目名称：LiveDataBus
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2019/4/9 9:31 AM
 * 修改人：yuliyan
 * 修改时间：2019/4/9 9:31 AM
 * 修改备注：
 */
public class SanXing {
    
    public SanXing(String phoneName, String phoneVersion) {
        this.phoneName = phoneName;
        this.phoneVersion = phoneVersion;
    }
    
    private String phoneName;
    private String phoneVersion;
    
    public String getPhoneName() {
        return phoneName;
    }
    
    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }
    
    public String getPhoneVersion() {
        return phoneVersion;
    }
    
    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }
    
    @Override
    public String toString() {
        return "SanXing{" +
            "phoneName='" + phoneName + '\'' +
            ", phoneVersion='" + phoneVersion + '\'' +
            '}';
    }
}
