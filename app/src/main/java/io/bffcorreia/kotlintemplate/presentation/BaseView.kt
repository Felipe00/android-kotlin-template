package io.bffcorreia.spontaneousevents.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import butterknife.ButterKnife
import io.bffcorreia.spontaneousevents.common.di.ActivityComponent
import io.bffcorreia.spontaneousevents.common.di.ViewComponent
import io.bffcorreia.spontaneousevents.common.di.ViewModule

abstract class BaseView : FrameLayout {

  lateinit protected var viewComponent: ViewComponent

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr) {
    init()
  }

  protected abstract val layoutRes: Int

  protected abstract fun initializeInjector()

  private val activityComponent: ActivityComponent
    get() = (context as BaseActivity).activityComponent

  private val viewModule: ViewModule
    get() = ViewModule(this)

  private fun init() {
    initViewComponent()
    initializeInjector()
    layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    View.inflate(context, layoutRes, this)
    ButterKnife.bind(this)
  }

  private fun initViewComponent() {
    viewComponent = activityComponent.plus(viewModule)
  }
}