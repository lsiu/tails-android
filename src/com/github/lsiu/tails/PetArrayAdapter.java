package com.github.lsiu.tails;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lsiu.tails.model.Pet;

public class PetArrayAdapter extends ArrayAdapter<Pet> {

	public PetArrayAdapter(Context context, int resource,
			int textViewResourceId, List<Pet> objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public PetArrayAdapter(Context context, int resource,
			int textViewResourceId, Pet[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public PetArrayAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public PetArrayAdapter(Context context, int resource, List<Pet> objects) {
		super(context, resource, objects);
	}

	public PetArrayAdapter(Context context, int resource, Pet[] objects) {
		super(context, resource, objects);
	}

	public PetArrayAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.activity_list_pet, parent, false);
		
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		
		textView.setText(this.getItem(position).getName());
		imageView.setImageResource(R.drawable.ic_launcher);

		return rowView;
	}

}
