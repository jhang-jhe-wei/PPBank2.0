import android.app.Activity
import android.app.AlertDialog
import com.homi.ppbank20.R

class LoadingDialog internal constructor(private val activity: Activity) {
    private var dialog: AlertDialog? = null
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading,null))
        builder.setCancelable(true)
        dialog = builder.create()
    }
    fun show(){
        dialog?.show()
    }
    fun dismiss() {
        dialog?.dismiss()
    }

    fun hide() {
        dialog?.hide()
    }

}