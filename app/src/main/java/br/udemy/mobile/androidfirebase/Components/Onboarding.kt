package br.udemy.mobile.androidfirebase.Components

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.absoluteValue
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat.startActivity
import br.udemy.mobile.androidfirebase.Activities.Home
import br.udemy.mobile.androidfirebase.Activities.LoginActivity
import br.udemy.mobile.androidfirebase.R


class Onboarding @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Indicator Colors
    private var pageIndicatorRadius: Float = 0f
    private var pageIndicatorStrokeWidth: Float = 0f
    private var pageIndicatorStrokeColor: Int = 0
    private var pageIndicatorColor: Int = 0
    private var pageIndicatorSelectedColor: Int = 0

    // Background Colors
    private var pageBackgroundColor: Int = 0

    private var nextPage: Int = 99

    private val paint = Paint()

    private var aceleration = 0f

    private var positionCanvasOne = 0f
    private var positionCanvasTwo = 0f
    private var positionCanvasThree = 0f

    private var fixedCanvasOne = 0f
    private var fixedCanvasTwo = 0f
    private var fixedCanvasThree = 0f

    private var position = 0f
    private var porcentagemAceleracao = 0f

    private var bmp1 = BitmapFactory.decodeResource(resources, R.drawable.background)
    private var bmp2 = BitmapFactory.decodeResource(resources, R.drawable.background_2)
    private var bmp3 = BitmapFactory.decodeResource(resources, R.drawable.background_3)

    private var image_1 = Bitmap.createScaledBitmap(bmp1, 1080, 1978, false)
    private var image_2 = Bitmap.createScaledBitmap(bmp2, 1080, 1978, false)
    private var image_3 = Bitmap.createScaledBitmap(bmp3, 1080, 1978, false)


    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Onboarding)

        pageIndicatorRadius = typedArray.getFloat(R.styleable.Onboarding_pageIndicatorRadius, 30f)
        pageIndicatorStrokeWidth = typedArray.getFloat(R.styleable.Onboarding_pageIndicatorStrokeWidth, 10f)
        pageIndicatorStrokeColor = typedArray.getColor(R.styleable.Onboarding_pageIndicatorStrokeColor, Color.BLACK)

        pageIndicatorColor = typedArray.getColor(R.styleable.Onboarding_pageIndicatorColor, Color.GRAY)
        pageIndicatorSelectedColor =
            typedArray.getColor(R.styleable.Onboarding_pageIndicatorSelectedColor, Color.DKGRAY)

        pageBackgroundColor = typedArray.getColor(R.styleable.Onboarding_pageBackgroundColor, Color.RED)


        typedArray.recycle()
    }

    fun next() {
        if (nextPage == 2) {
            val teste = Intent(context, LoginActivity::class.java)
            startActivity(context, teste, null)
        } else {
            if(nextPage < 2) nextPage++ else nextPage
            aceleration = -10f
        }
    }

    fun previous() {
        if(nextPage != 0) nextPage-- else nextPage
        aceleration = 10f
    }

    override fun onDraw(canvas: Canvas) {

        //Draw Image
        drawImage(canvas)
    }

    private fun drawImage(canvas: Canvas) {

        if (this.nextPage == 99) {


            fixedCanvasOne = 0f
            fixedCanvasTwo = fixedCanvasOne + width
            fixedCanvasThree = fixedCanvasTwo + width

            positionCanvasOne = 0f
            positionCanvasTwo = (width * 1f)
            positionCanvasThree = (width * 2f)

            position = positionCanvasOne
            porcentagemAceleracao = 0f

            image_1 = Bitmap.createScaledBitmap(bmp1, measuredWidth, measuredHeight, false)
            image_2 = Bitmap.createScaledBitmap(bmp2, measuredWidth, measuredHeight, false)
            image_3 = Bitmap.createScaledBitmap(bmp3, measuredWidth, measuredHeight, false)
            nextPage = 0

            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            paint.textSize = 100f
        }

        if ((positionCanvasOne.absoluteValue.toInt() == 0 && nextPage == 0) ||
            (positionCanvasTwo.absoluteValue.toInt() == 0 && nextPage == 1) ||
            (positionCanvasThree.absoluteValue.toInt() == 0 && nextPage == 2)) {
            aceleration = 0f
            porcentagemAceleracao = 0f
        }

        if (nextPage == 0 && aceleration > 0) {
            position = width - positionCanvasOne.absoluteValue
        } else if (nextPage == 1) {
            position = if (aceleration > 0) width - positionCanvasTwo.absoluteValue else positionCanvasOne.absoluteValue
        } else if (nextPage == 2) {
            position = if (aceleration > 0) width - positionCanvasTwo.absoluteValue else positionCanvasTwo.absoluteValue
        }

        if (aceleration.absoluteValue > 0) {

            val percentual = (position * 100) / width

            if (percentual < 10 || percentual > 90) {
                porcentagemAceleracao = aceleration
            } else if ((percentual > 10 && percentual < 30) || (percentual > 70 && percentual < 90)) {
                porcentagemAceleracao += aceleration
            }

        }

        positionCanvasOne = positionCanvasOne.plus(porcentagemAceleracao)
        positionCanvasTwo = positionCanvasTwo.plus(porcentagemAceleracao)
        positionCanvasThree = positionCanvasThree.plus(porcentagemAceleracao)

        canvas.drawBitmap(image_1, positionCanvasOne, 0f, paint)
        canvas.drawBitmap(image_2, positionCanvasTwo, 0f, paint)
        canvas.drawBitmap(image_3, positionCanvasThree, 0f, paint)

        invalidate()

    }

}