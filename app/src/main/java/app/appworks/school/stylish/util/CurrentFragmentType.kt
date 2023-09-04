package app.appworks.school.stylish.util

import app.appworks.school.stylish.R
import app.appworks.school.stylish.util.Util.getString

enum class CurrentFragmentType(val value: String) {
    HOME(""),
    CATALOG(getString(R.string.catalog)),
    FAVORITE(getString(R.string.favorite)),
    CART(getString(R.string.cart)),
    PROFILE(getString(R.string.profile)),
    PAYMENT(getString(R.string.payment)),
    OrderHistory(getString(R.string.order_history)),
    DetailOrder(getString(R.string.detail_order)),
    DETAIL(""),
    CHECKOUT_SUCCESS(getString(R.string.checkout_success_title))
}
