/***
  Copyright (c) 2012 CommonsWare, LLC
  
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

package com.commonsware.cwac.richedit.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.Menu;
import android.view.MenuItem;
import com.commonsware.cwac.colormixer.ColorMixerActivity;
import com.commonsware.cwac.richedit.ColorPicker;
import com.commonsware.cwac.richedit.ColorPickerOperation;
import com.commonsware.cwac.richedit.RichEditText;
import com.commonsware.cwac.richedit.URLEffect;

public class RichTextEditorDemoActivity extends Activity
  implements ColorPicker {
  private static final int COLOR_REQUEST=1337;
  private RichEditText editor=null;
  private ColorPickerOperation colorPickerOp=null;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.main);
    
    editor=(RichEditText)findViewById(R.id.editor);
    editor.setColorPicker(this);
    editor.enableActionModes(true);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.actions, menu);

    return(super.onCreateOptionsMenu(menu));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case R.id.add_link:
        int offset=editor.getText().length();
        editor.append(" CommonsWare rocks!");
        editor.setSelection(offset+1, editor.getText().length());
        editor.applyEffect(new URLEffect(), "https://commonsware.com");

        Selection.removeSelection(editor.getText());

        return(true);
    }

    return(super.onOptionsItemSelected(item));
  }

  @Override
  public boolean pick(ColorPickerOperation op) {
    Intent i=new Intent(this, ColorMixerActivity.class);

    i.putExtra(ColorMixerActivity.TITLE, "Pick a Color");

    if (op.hasColor()) {
      i.putExtra(ColorMixerActivity.COLOR, op.getColor());
    }

    this.colorPickerOp=op;
    startActivityForResult(i, COLOR_REQUEST);

    return(true);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode,
                               Intent result) {
    int color=0;

    if (colorPickerOp!=null && requestCode==COLOR_REQUEST) {
      if (resultCode==Activity.RESULT_OK) {
        color=result.getIntExtra(ColorMixerActivity.COLOR, 0);
      }

      if (color==0) {
        colorPickerOp.onPickerDismissed();
      }
      else {
        colorPickerOp.onColorPicked(color);
      }

      colorPickerOp=null;
    }
  }
}
