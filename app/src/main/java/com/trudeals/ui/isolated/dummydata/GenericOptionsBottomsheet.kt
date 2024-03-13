package com.trudeals.ui.isolated.dummydata

import com.trudeals.ui.isolated.optionsbottomsheet.Option
import com.trudeals.utils.PropertyListTag
import com.trudeals.utils.TypeOfDeals

data class MinMaxPrice(override val option: String, override var isSelected: Boolean) : Option
data class SelectBeds(override val option: String, override var isSelected: Boolean) : Option
data class SelectBathrooms(override val option: String, override var isSelected: Boolean) : Option
data class SelectGarageSize(override val option: String, override var isSelected: Boolean) : Option
data class SelectAreaUnit(override val option: String, override var isSelected: Boolean) : Option
data class SelectReasonToCancel(override val option: String, override var isSelected: Boolean) : Option
data class SelectPropertyType(override val option: String, override var isSelected: Boolean) : Option
data class SelectedUserType(override val option: String, override var isSelected: Boolean) : Option
data class SelectTypeOfDeal(override val option: String, override var isSelected: Boolean, val tag: TypeOfDeals) : Option
data class SelectRentDurationType(
    override val option: String,
    override var isSelected: Boolean,
    var tag: PropertyListTag
) : Option
