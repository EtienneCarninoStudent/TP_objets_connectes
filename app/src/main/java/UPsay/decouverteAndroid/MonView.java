package UPsay.decouverteAndroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MonView extends View {

    Bitmap b;
    float xText, yText;
    float tailleTexte = 0;
    float x1, x2, y1, y2;

    /* timer : run() est appelée toutes les 100 ms */
    Handler timerHandler = new Handler();
    Runnable updateTimerThread = new Runnable() {
        public void run() {
            timerHandler.postDelayed(this, 100);
            /* faire grandir le texte à partir de 0, puis recommencer */
            tailleTexte += 5;
            if (tailleTexte > 150) {
                tailleTexte = 0;
            }
            invalidate();   // appel de onDraw pour redessiner
        }
    };

    public MonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setXYText(600, 600);
        timerHandler.postDelayed(updateTimerThread, 10);
        /* charger l'image depuis res/drawable */
        b = BitmapFactory.decodeResource(getResources(), R.drawable.smiley);
        /* découper un rectangle de 50x50 positionné en (25,25) : le centre du smiley */
        b = Bitmap.createBitmap(b, 25, 25, 50, 50);

        /* remplace onTouchEvent : on s'abonne aux évènements tactiles */
        OnTouchListener onTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float dx, dy;
                String direction;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        /* point de contact */
                        x1 = event.getX();
                        y1 = event.getY();
                        Log.i("pacman", "appuyé");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        /* le texte suit le doigt */
                        xText = event.getX();
                        yText = event.getY();
                        break;
                    case MotionEvent.ACTION_UP: {
                        /* point de relâchement */
                        x2 = event.getX();
                        y2 = event.getY();
                        dx = x2 - x1;
                        dy = y2 - y1;
                        /* dx et dy donnent la direction du mouvement */
                        if (Math.abs(dx) > Math.abs(dy)) {
                            if (dx > 0)
                                direction = "right";
                            else
                                direction = "left";
                        } else {
                            if (dy > 0)
                                direction = "down";
                            else
                                direction = "up";
                        }
                        Log.i("pacman", "lâché " + direction);
                        Log.i("pacman", "dx = " + dx + "; dy = " + dy);
                        break;
                    }
                }
                invalidate();
                return true;    // l'évènement a été traité
            }
        };
        setOnTouchListener(onTouchListener);
    }

    public void setXYText(float x, float y) {
        xText = x;
        yText = y;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint p = new Paint();
        /* définir la couleur de l'objet de dessin */
        p.setColor(Color.BLACK);
        /* définir son style en remplissage */
        p.setStyle(Paint.Style.FILL);
        /* dessiner un rectangle qui occupe la totalité du View */
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        /* définir une autre couleur pour dessiner un texte */
        p.setColor(Color.GREEN);
        /* définir la taille du texte */
        p.setTextSize(tailleTexte);
        /* définir le centre du texte comme étant son origine */
        p.setTextAlign(Paint.Align.CENTER);
        /* dessiner le texte en positionnant son origine au centre du View */
        String texte = "Bonjour MONDE";
        canvas.drawText(texte, xText, yText, p);
        /* afficher l'image découpée à la taille 200x200, à partir du point (200,200) */
        canvas.drawBitmap(b, null, new Rect(200, 200, 400, 400), p);
    }
}
