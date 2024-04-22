package pl.inpost.recruitmenttask.presentation.shipmentScreen.model

import pl.inpost.recruitmenttask.domain.model.SortType

fun SortType.toLastUserAction(): LastUserAction {
    return when (this) {
        SortType.ByStatus -> LastUserAction.STATUS
        SortType.ByNumber -> LastUserAction.NUMBER
        SortType.ByPickupDate -> LastUserAction.PICKUP_DATE
        SortType.ByExpireDate -> LastUserAction.EXPIRE_DATE
        SortType.ByStoredDate -> LastUserAction.STORED_DATE
    }
}

fun SortType.toTitle(): String {
    return when (this) {
        SortType.ByStatus -> "Sorted by Status"
        SortType.ByNumber -> "Sorted by Number"
        SortType.ByPickupDate -> "Sorted by Pickup Date"
        SortType.ByExpireDate -> "Sorted by Expire Date"
        SortType.ByStoredDate -> "Sorted by Stored Date"
    }
}