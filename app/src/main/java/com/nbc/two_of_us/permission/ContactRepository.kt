package com.nbc.two_of_us.permission

class ContactRepository(
    datasource: ContactDatasource,
    permissionManager: PermissionManager
) {
    val contactInfoList = datasource.contactInfoList


}
