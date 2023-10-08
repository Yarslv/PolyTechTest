package com.polytechtest.ext

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.polytechtest.R
import com.polytechtest.ui.fragments.book_list.FragmentBookListDirections
import com.polytechtest.ui.fragments.categories.CategoriesFragmentDirections
import com.polytechtest.util.AppAction
import com.polytechtest.util.CustomException

fun Fragment.doAction(action: AppAction) {
    when (action) {
        is AppAction.Navigate.ToBookList -> findNavController().navigate(
            CategoriesFragmentDirections.actionCategoriesFragmentToFragmentBookList(action.encodedName)
        )

        is AppAction.Navigate.ToBuyBook ->
            if (findNavController().currentDestination?.id == R.id.fragmentBookList &&
                lifecycle.currentState == androidx.lifecycle.Lifecycle.State.RESUMED
            )
                findNavController().navigate(
                    FragmentBookListDirections.actionFragmentBookListToDialogBuy(action.url)
                )

        is AppAction.Message -> Toast.makeText(
            requireContext(),
            if (action.e is CustomException)
                when (action.e) {
                    CustomException.NetworkException -> getString(R.string.no_connection_message)
                    CustomException.RoomException -> getString(R.string.no_cached_data_message)
                } else getString(R.string.something_went_wrong),
            Toast.LENGTH_SHORT
        ).show()
    }
}
