//import android.graphics.drawable.PictureDrawable
//import com.bumptech.glide.load.engine.Resource
//import com.bumptech.glide.load.resource.SimpleResource
//import com.bumptech.glide.load.resource.drawable.DrawableResource
//
//// Custom implementation of DrawableResource for PictureDrawable
//class SvgDrawableResource(drawable: PictureDrawable) : Resource<PictureDrawable> {
//    private val drawableResource = SimpleResource(drawable)
//
//    override fun get(): PictureDrawable = drawableResource.get()
//
//    override fun recycle() {
//        drawableResource.recycle()
//    }
//
//    override fun getSize(): Int = drawableResource.getSize()
//
//    override fun getResourceClass(): Class<PictureDrawable> = PictureDrawable::class.java
//}
//
