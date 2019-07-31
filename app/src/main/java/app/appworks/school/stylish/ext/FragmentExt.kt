package app.appworks.school.stylish.ext

import androidx.fragment.app.Fragment
import app.appworks.school.stylish.StylishApplication
import app.appworks.school.stylish.factory.ViewModelFactory
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.User
import app.appworks.school.stylish.factory.ProductViewModelFactory
import app.appworks.school.stylish.factory.ProfileViewModelFactory

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as StylishApplication).stylishRepository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(user: User?): ProfileViewModelFactory {
    val repository = (requireContext().applicationContext as StylishApplication).stylishRepository
    return ProfileViewModelFactory(repository, user)
}

fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
    val repository = (requireContext().applicationContext as StylishApplication).stylishRepository
    return ProductViewModelFactory(repository, product)
}