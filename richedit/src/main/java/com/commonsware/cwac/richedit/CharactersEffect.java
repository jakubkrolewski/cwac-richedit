package com.commonsware.cwac.richedit;

import com.commonsware.cwac.richtextutils.Selection;

public abstract class CharactersEffect<T> extends Effect<T> {

    @Override
    public boolean canBeAppliedToSelection(RichEditText editor) {
        Selection selection = new Selection(editor);

        return selection.getStart() != selection.getEnd();
    }
}
