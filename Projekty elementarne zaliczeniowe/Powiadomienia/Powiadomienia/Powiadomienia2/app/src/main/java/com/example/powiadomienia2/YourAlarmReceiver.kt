import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.util.Calendar

class YourAlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        Toast.makeText(context, "Alarm triggered!", Toast.LENGTH_SHORT).show()

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, YourAlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_MUTABLE)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 10)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}