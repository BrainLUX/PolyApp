package com.polytech.application.templates

import com.polytech.application.base.BaseModel

data class TemplateModel(override val id: Long = 0, override val value: String = "") :
    BaseModel(id, value)