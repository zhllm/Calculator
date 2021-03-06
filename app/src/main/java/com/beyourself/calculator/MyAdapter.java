package com.beyourself.calculator;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.beyourself.calculator.DTO.Word;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ListAdapter<Word, MyAdapter.MyViewHolder> {
    boolean useCardView;
    private WordViewModel wordViewModel;

    public MyAdapter(boolean useCardView, WordViewModel wordViewModel) {
        super(new DiffUtil.ItemCallback<Word>() {
            @Override
            public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return
                        oldItem.getWord().equals(newItem.getWord()) &&
                                oldItem.getChineseMeaning().equals(newItem.getChineseMeaning());
            }
        });
        this.useCardView = useCardView;
        this.wordViewModel = wordViewModel;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.textViewIndex.setText(String.valueOf(holder.getAdapterPosition() + 1));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (this.useCardView) {
            itemView = layoutInflater.inflate(R.layout.cell_card2, parent, false);
        } else {
            itemView = layoutInflater.inflate(R.layout.cel_normal2, parent, false);
        }
        MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=" + holder.textViewEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Word word = (Word) holder.itemView.getTag(R.id.view_for_holder);
                if (isChecked) {
                    holder.textViewChinese.setVisibility(View.INVISIBLE);
                    word.setVisible(true);
                    wordViewModel.updateWords(word);
                } else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setVisible(false);
                    wordViewModel.updateWords(word);
                }
                Log.d("MyTag", String.valueOf(word.isVisible()));
                Log.d("MyTag", String.valueOf(word.isVisible()));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word word = getItem(position);
        holder.itemView.setTag(R.id.view_for_holder, word);
        holder.textViewIndex.setText(String.valueOf(position + 1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        if (word.isVisible()) {
            holder.textViewChinese.setVisibility(View.INVISIBLE);
            holder.aSwitch.setChecked(true);
        } else {
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitch.setChecked(false);
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIndex, textViewEnglish, textViewChinese;
        Switch aSwitch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewChinese = itemView.findViewById(R.id.adapter_chinese);
            textViewEnglish = itemView.findViewById(R.id.adapter_english);
            textViewIndex = itemView.findViewById(R.id.adapter_index);
            aSwitch = itemView.findViewById(R.id.aswitch);
        }
    }
}
