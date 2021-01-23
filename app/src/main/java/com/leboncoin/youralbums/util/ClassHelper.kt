package com.leboncoin.youralbums.util

object ClassHelper {

    fun toString(`object`: Any?): String? {
        if (`object` == null) {
            return null
        }
        val result = StringBuilder()
        val newLine = System.getProperty("line.separator")
        result.append(`object`.javaClass.simpleName)
        result.append(" Object {")
        result.append(newLine)

        //determine fields declared in this class only (no fields of superclass)
        val fields = `object`.javaClass.declaredFields

        //print field names paired with their values
        for (field in fields) {
            result.append("  ")
            try {
                result.append(field.name)
                result.append(": ")
                //requires access to private field:
                field.isAccessible = true
                result.append(field[`object`])
            } catch (ex: IllegalAccessException) {
                println(ex)
            }
            result.append(newLine)
        }
        result.append("}")
        return result.toString()
    }

}

