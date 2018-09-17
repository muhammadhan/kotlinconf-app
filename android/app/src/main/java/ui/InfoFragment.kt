package org.jetbrains.kotlinconf.ui

import android.content.*
import android.graphics.*
import android.graphics.drawable.*
import android.net.*
import android.os.*
import android.support.design.widget.*
import android.support.design.widget.AppBarLayout.LayoutParams.*
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.*
import android.support.v4.app.*
import android.support.v7.app.*
import android.support.v7.widget.Toolbar
import android.text.util.*
import android.view.*
import android.widget.*
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.*
import org.jetbrains.anko.support.v4.*
import org.jetbrains.kotlinconf.*
import org.jetbrains.kotlinconf.R

class InfoFragment : Fragment(), AnkoComponent<Context> {

    private lateinit var toolbar: Toolbar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(context!!))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        coordinatorLayout {
            backgroundColor = Color.WHITE
            themedAppBarLayout(R.style.ThemeOverlay_AppCompat_ActionBar) {
                multilineCollapsingToolbarLayout {
                    relativeLayout {
                        backgroundColor = Color.WHITE
                        contentScrim = ColorDrawable(Color.WHITE)

                        layoutParams = CollapsingToolbarLayout.LayoutParams(matchParent, matchParent).apply {
                            collapseMode = COLLAPSE_MODE_PARALLAX
                        }

                        imageView(R.drawable.kotlin_conf_app_header).lparams {
                            margin = dip(20)
                        }
                        imageView(R.drawable.kotlin_conf_app_header).scaleType = ImageView.ScaleType.FIT_CENTER
                    }

                    toolbar = toolbar {
                        layoutParams = CollapsingToolbarLayout.LayoutParams(
                                matchParent,
                                context.dimen(context.getResourceId(R.attr.actionBarSize))
                        ).apply {
                            collapseMode = COLLAPSE_MODE_PIN
                        }
                    }
                }.lparams(width = matchParent, height = matchParent) {
                    scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }
            }.lparams(width = matchParent, height = dip(200))

            nestedScrollView {
                verticalLayout {
                    view {
                        backgroundResource = context.getResourceId(android.R.attr.listDivider)
                    }.lparams(width = matchParent, height = dip(2))

                    textView(context.getHtmlText(R.string.app_description)) {
                        autoLinkMask = Linkify.WEB_URLS
                        textSize = 18f
                        setTextIsSelectable(true)
                    }.lparams {
                        margin = dip(20)
                    }

                    view {
                        backgroundResource = context.getResourceId(android.R.attr.listDivider)
                    }.lparams(width = matchParent, height = dip(2))

                    relativeLayout {
                        padding = dip(20)

                        imageView(R.drawable.ic_location) {
                            id = R.id.icon_location

                            setOnClickListener {
                                val gmmIntentUri = Uri.parse("geo:52.3752,4.8960?z=17")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.`package` = "com.google.android.apps.maps"
                                if (mapIntent.resolveActivity(context.packageManager) != null) {
                                    startActivity(mapIntent)
                                }
                            }

                        }.lparams(width = dip(24), height = dip(24)) {
                            centerVertically()
                            leftMargin = dip(10)
                            rightMargin = dip(20)
                        }

                        textView(R.string.kotlin_conf_address) {
                            textSize = 18f
                            textColor = Color.BLACK
                            setTextIsSelectable(true)
                        }.lparams {
                            centerVertically()
                            rightOf(R.id.icon_location)
                        }
                    }

                    view {
                        backgroundResource = context.getResourceId(android.R.attr.listDivider)
                    }.lparams(width = matchParent, height = dip(2))

                    linearLayout {
                        relativeLayout {
                            imageView(R.drawable.ic_web) {
                                id = R.id.icon_website
                            }.lparams(width = dip(24), height = dip(24)) {
                                centerHorizontally()
                            }

                            textView("WEBSITE") {
                                textSize = 16f
                                textColor = theme.getColor(R.attr.colorAccent)
                            }.lparams {
                                centerHorizontally()
                                bottomOf(R.id.icon_website)
                                topMargin = dip(10)
                            }

                            setOnClickListener {
                                val websiteIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://kotlinconf.com"))
                                startActivity(websiteIntent)
                            }

                        }.lparams(width = 0, height = wrapContent) {
                            weight = 0.5f
                        }

                        relativeLayout {
                            imageView(R.drawable.ic_twitter) {
                                id = R.id.icon_twitter
                            }.lparams(width = dip(24), height = dip(24)) {
                                centerHorizontally()
                            }

                            textView("TWITTER") {
                                textSize = 16f
                                textColor = theme.getColor(R.attr.colorAccent)
                            }.lparams {
                                centerHorizontally()
                                bottomOf(R.id.icon_twitter)
                                topMargin = dip(10)
                            }

                            setOnClickListener {
                                val twitterIntent = Intent(Intent.ACTION_VIEW,
                                        Uri.parse("twitter://user?screen_name=kotlinconf"))
                                twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                if (twitterIntent.resolveActivity(context.packageManager) != null) {
                                    startActivity(twitterIntent)
                                } else {
                                    val webTwitterIntent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://twitter.com/kotlinconf"))
                                    startActivity(webTwitterIntent)
                                }
                            }

                        }.lparams(width = 0, height = wrapContent) {
                            weight = 0.5f
                        }
                    }.lparams(width = matchParent, height = wrapContent) {
                        margin = dip(30)
                    }

                    view {
                        backgroundResource = context.getResourceId(android.R.attr.listDivider)
                    }.lparams(width = matchParent, height = dip(2))

                    textView(R.string.legal_notice_title) {
                        setTextIsSelectable(true)
                    }.lparams {
                        gravity = Gravity.CENTER_HORIZONTAL
                        margin = dip(20)
                        bottomMargin = dip(5)
                    }

                    textView(R.string.copyright) {
                        setTextIsSelectable(true)
                    }.lparams {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView(R.string.libraries) {
                        setTextIsSelectable(true)
                    }.lparams {
                        topMargin = dip(20)
                        leftMargin = dip(20)
                        rightMargin = dip(20)
                    }

                    textView(context.getHtmlText(R.string.apache2_license)) {
                        setTextIsSelectable(true)
                    }.lparams {
                        margin = dip(20)
                        bottomMargin = dip(40)
                    }
                }.lparams(width = matchParent, height = matchParent)
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
    }

    companion object {
        const val TAG = "Info"
    }
}