//import android.graphics.drawable.PictureDrawable
//import com.bumptech.glide.load.Options
//import com.bumptech.glide.load.engine.Resource
//import com.bumptech.glide.load.resource.SimpleResource
//import com.bumptech.glide.load.resource.drawable.DrawableResource
//import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
//import com.caverock.androidsvg.SVG
//import java.io.InputStream
//
//// Custom SVG decoder
//class SvgDecoder : com.bumptech.glide.load.ResourceDecoder<InputStream, SVG> {
//    override fun handles(source: InputStream, options: Options): Boolean {
//        return true
//    }
//
//    override fun decode(source: InputStream, width: Int, height: Int, options: Options): Resource<SVG>? {
//        return try {
//            val svg = SVG.getFromInputStream(source)
//            SimpleResource(svg)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//}
//
//// Transcoder to convert SVG to PictureDrawable
//class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
//    override fun transcode(toTranscode: Resource<SVG>, options: Options): Resource<PictureDrawable>? {
//        val svg = toTranscode.get()
//        val picture = svg.renderToPicture()
//        return SimpleResource(PictureDrawable(picture))
//    }
//}
