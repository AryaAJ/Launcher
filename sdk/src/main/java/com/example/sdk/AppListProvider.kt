package com.example.sdk

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import com.example.sdk.model.AppData


object AppListProvider {

    fun getList(context: Context): ArrayList<AppData> {
        val list: ArrayList<AppData> = ArrayList()

        val getSysPackages = false
        val packs: List<PackageInfo> = context.packageManager.getInstalledPackages(0)

        for (i in packs.indices) {
            val p = packs[i]
            if (!getSysPackages && p.versionName == null) {
                continue
            }
            val newInfo = AppData()

            newInfo.app_name = p.applicationInfo.loadLabel(context.packageManager).toString()
            newInfo.package_name = p.packageName
            newInfo.icon = p.applicationInfo.loadIcon(context.packageManager)
            newInfo.main_activity_name = p.applicationInfo.className
            newInfo.version_code = p.versionCode.toString()
            newInfo.version = p.versionName
            newInfo.install_data = p.applicationInfo.className
            newInfo.flags = p.applicationInfo.flags
            if (isSystemPackage(p.applicationInfo.flags)) {
                list.add(newInfo)
            }
        }

        val max: Int = list.size
        for (i in 0 until max) {
            list[i]
        }

        list.sortBy { appData -> appData.app_name }

        return list
    }

    private fun isSystemPackage(flag: Int): Boolean {
        return flag and ApplicationInfo.FLAG_SYSTEM == 0
    }
}

