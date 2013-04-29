package com.franlopez.imagegalleryexample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.franlopez.imagegalleryexample.R;

public class ImageGalleryActivity extends Activity implements
		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {

	private ImageSwitcher mSwitcher;
	private TextView mName;
	private RelativeLayout mRootNode;
	private boolean open = false;
	private Context mContext;
	private int mPosition;

	/**
	 * Array con los distintos nombres de las imágenes
	 */
	private String[] mNames = { "Árbol 1", "Árbol 2", "Árbol 3",
			"Árbol 4", "Árbol 5", "Árbol 6", "Árbol 7", "Árbol 8", "Árbol 9", "Árbol 10", "Árbol 11", "Árbol 12"};

	/**
	 * Array con los THumbs de las imágenes a mostrar (se pueden poner las
	 * mismas imágenes que en el otro ya que se redimencionarán
	 */
	private Integer[] mThumbIds = { R.drawable.arbol1,
			R.drawable.arbol2, R.drawable.arbol3, R.drawable.arbol4, R.drawable.arbol5, R.drawable.arbol6, R.drawable.arbol1,
			R.drawable.arbol2, R.drawable.arbol3, R.drawable.arbol4, R.drawable.arbol5, R.drawable.arbol6};

	/**
	 * Array con las imágenes a mostrar en la galería
	 */
	private Integer[] mImageIds = { R.drawable.arbol1,
			R.drawable.arbol2, R.drawable.arbol3, R.drawable.arbol4, R.drawable.arbol5, R.drawable.arbol6, R.drawable.arbol1,
			R.drawable.arbol2, R.drawable.arbol3, R.drawable.arbol4, R.drawable.arbol5, R.drawable.arbol6 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_imagegallery);

		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		mName = (TextView) findViewById(R.id.txtImage);
		mRootNode = (RelativeLayout) findViewById(R.id.rootNode);

		mContext = this;

		final ImageView i = new ImageView(mContext);

		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this));
		g.setOnItemSelectedListener(this);
		g.setSelection(mPosition);

	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		mSwitcher.setImageResource(mImageIds[position]);
		mName.setText(mNames[position]);
		mPosition = position;
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}

	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	/**
	 * Adapter encargado de mostrar las imágenes de la galería, mostrar los
	 * distintos nombres de las imágenes y además mostrar la preview de cada
	 * imagen
	 * 
	 * @author FranLopez
	 * 
	 */
	public class ImageAdapter extends BaseAdapter {

		private int mPosition;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView i = new ImageView(mContext);
			mPosition = position;
			final Gallery g = (Gallery) findViewById(R.id.gallery);

			// Evento para controlar los movimientos en la pantalla con el fin
			// de mostrar
			// la siguiente imagen o la anterior
			mRootNode.setOnTouchListener(new OnSwipeTouchListener() {
				public void onSwipeRight() {
					if ((mPosition - 1) < 0) {
						mSwitcher.setImageResource(mImageIds[mPosition]);
						i.setImageResource(mThumbIds[mPosition]);
						mName.setText(mNames[mPosition]);
						g.setSelection(mPosition, false);
					} else {
						mSwitcher.setImageResource(mImageIds[mPosition - 1]);
						i.setImageResource(mThumbIds[mPosition - 1]);
						mName.setText(mNames[mPosition - 1]);
						g.setSelection(mPosition, false);
						g.setSelection(mPosition - 1, false);
					}

				}

				public void onSwipeLeft() {
					if ((mPosition + 1) >= mImageIds.length) {
						mSwitcher.setImageResource(mImageIds[mPosition]);
						i.setImageResource(mThumbIds[mPosition]);
						mName.setText(mNames[mPosition]);
						g.setSelection(mPosition, false);

					} else {
						mSwitcher.setImageResource(mImageIds[mPosition + 1]);
						i.setImageResource(mThumbIds[mPosition + 1]);
						mName.setText(mNames[mPosition + 1]);
						g.setSelection(mPosition + 1, false);
					}
				}
			});

			i.setImageResource(mThumbIds[mPosition]);
			i.setAdjustViewBounds(true);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			// Añadimos el marco de la foto
			i.setBackgroundColor(Color.WHITE);
			i.setPadding(2, 2, 2, 2);

			// i.setBackgroundResource(R.drawable.picture_frame);
			return i;
		}

		private Context mContext;

	}

}