package app.cash.paykit.sample.ui.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import app.cash.paykit.sample.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {

  companion object {
    fun newInstance() = CheckoutFragment()
  }

  private var _binding: FragmentCheckoutBinding? = null
  private val binding get() = _binding!!
  private val viewModel: MainViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}
