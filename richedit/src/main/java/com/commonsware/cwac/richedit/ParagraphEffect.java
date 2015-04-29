package com.commonsware.cwac.richedit;

import android.text.Spannable;
import com.commonsware.cwac.richtextutils.Selection;

public abstract class ParagraphEffect<T> extends Effect<T> {

    protected abstract void applyToParagraphSelection(Selection paragraphSelection, Spannable str, T value);

    @Override
    public final void applyToSelection(Spannable str, Selection selection, T value) {
        Selection paragraphSelection=selection.extendToFullLine(str);

        applyToParagraphSelection(paragraphSelection, str, value);
    }

    @Override
    public boolean canBeAppliedToSelection(RichEditText editor) {
        return true;
    }
}
