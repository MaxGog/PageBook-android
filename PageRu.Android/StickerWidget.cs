using Android.App;
using Android.Appwidget;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PageRu.Droid
{
    [BroadcastReceiver(Label = "@string/widget_name")]
    [IntentFilter(new string[] { "android.appwidget.action.APPWIDGET_UPDATE" })]
    [MetaData("android.appwidget.provider", Resource = "@xml/widget")]
    public class StickerWidget : AppWidgetProvider
    {
        public override void OnUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
        {
            context.StartService(new Intent(context, typeof(UpdateService)));
        }
    }
}