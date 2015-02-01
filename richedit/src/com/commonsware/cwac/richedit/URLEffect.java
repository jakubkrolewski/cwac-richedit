/***
  Copyright (c) 2008-2015 CommonsWare, LLC
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.commonsware.cwac.richedit;

import android.text.Spannable;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;

public class URLEffect extends Effect<String> {
  @Override
  boolean existsInSelection(RichEditText editor) {
    Selection selection=new Selection(editor);
    Spannable str=editor.getText();

    return(getURLSpans(str, selection).length > 0);
  }

  @Override
  String valueInSelection(RichEditText editor) {
    Selection selection=new Selection(editor);
    Spannable str=editor.getText();
    float max=0.0f;
    URLSpan[] spans=getURLSpans(str, selection);

    if (spans.length > 0) {
      return(spans[0].getURL());
    }

    return(null);
  }

  @Override
  void applyToSelection(RichEditText editor, String url) {
    Selection selection=new Selection(editor);
    Spannable str=editor.getText();

    for (URLSpan span : getURLSpans(str, selection)) {
      str.removeSpan(span);
    }

    if (url != null) {
      str.setSpan(new URLSpan(url), selection.start,
                  selection.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
  }

  private URLSpan[] getURLSpans(Spannable str, Selection selection) {
    return(str.getSpans(selection.start, selection.end,
                        URLSpan.class));
  }
}
