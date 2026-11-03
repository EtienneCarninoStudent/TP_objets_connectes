package UPsay.decouverteAndroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MonView extends View {

    Bitmap b;

    public MonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /* charger l'image depuis res/drawable */
        b = BitmapFactory.decodeResource(getResources(), R.drawable.smiley);
        /* découper un rectangle de 50x50 positionné en (25,25) : le centre du smiley */
        b = Bitmap.createBitmap(b, 25, 25, 50, 50);
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
        p.setTextSize(100);
        /* définir le centre du texte comme étant son origine */
        p.setTextAlign(Paint.Align.CENTER);
        /* dessiner le texte en positionnant son origine au centre du View */
        String texte = "Bonjour MONDE";
        canvas.drawText(texte, getWidth() / 2, getHeight() / 2, p);
        /* afficher l'image découpée à la taille 200x200, à partir du point (200,200) */
        canvas.drawBitmap(b, null, new Rect(200, 200, 400, 400), p);
    }
}
