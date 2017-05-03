package com.example.juniorf.mylastaplicationandroid;



import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

/**
 * Created by juniorf on 07/11/16.
 */

public class Notificacao {
    public static final int TIPO_ACTIVITY = 1;
    public static final int TIPO_BROADCAST = 2;
    public static final int TIPO_SERVICE = 3;
    public static final String INTENT_STRING_MSG_STATUS = "MSGs";
    public static final String INTENT_STRING_MSG = "MSG";
    public static final String INTENT_STRING_TITULO = "titu";
    public static final String INTENT_LONG_QUANDO = "WHEN";
    public static final String INTENT_INT_ICONE = "icone";
    public static final String INTENT_INT_FLAGS = "FLS";

    /**
     *
     * @param activity Atividade
     * @param param Parametros da notificacao
     * @param acao Ação quando é selecionado a notificacao
     * @param tipo Tipo da ação /broadcast/atividade/serviço/
     * @return Notification
     */
    public static Notification criarNotificacao(Activity activity, Intent param, Intent acao, int tipo) {
        //cria a notificacao
        Notification n = new Notification(param.getIntExtra(INTENT_INT_ICONE, 0),
                param.getStringExtra(INTENT_STRING_MSG_STATUS),
                param.getLongExtra(INTENT_LONG_QUANDO, System.currentTimeMillis()));

        //Responsavel pela acao caso o usuario selecione a notificacao;
       /* PendingIntent p;
        if (tipo == TIPO_ACTIVITY) {
            p = PendingIntent.getActivity(activity, 0, acao, param.getIntExtra(INTENT_INT_FLAGS, 0));
        } else if (tipo == TIPO_BROADCAST) {
            p = PendingIntent.getBroadcast(activity, 0, acao, param.getIntExtra(INTENT_INT_FLAGS, 0));
        } else if (tipo == TIPO_SERVICE) {
            p = PendingIntent.getService(activity, 0, acao, param.getIntExtra(INTENT_INT_FLAGS, 0));
        } else {
            throw new IllegalArgumentException("tipo indefinido");
        }

        //Vincula o PendingIntent com a notificacao
        n.setLatestEventInfo(activity, param.getStringExtra(INTENT_STRING_TITULO), param.getStringExtra(INTENT_STRING_MSG), p);
*/
        //Define valores default para a notificacao ex. som,vibra.
            n.defaults = Notification.DEFAULT_ALL;
            return n;

        }

    public static NotificationManager notificar(Activity activity, Notification notification, int id) {
        //pega serviço de notificacao
        NotificationManager nm = (NotificationManager) activity.getSystemService(Activity.NOTIFICATION_SERVICE);
        //exibe a notificacao
        nm.notify(id, notification);
        return nm;
    }

}
