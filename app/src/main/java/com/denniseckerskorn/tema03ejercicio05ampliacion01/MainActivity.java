package com.denniseckerskorn.tema03ejercicio05ampliacion01;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button[] buttons;
    private int rowCount;
    private int columnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencia al gridLayout:
        GridLayout glButtons = findViewById(R.id.glButtons);
        rowCount = 2;
        columnCount = 5;

        createButtons(glButtons, rowCount, columnCount);

        glButtons.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int gridWidth = glButtons.getWidth();

                int buttonSize = gridWidth / columnCount;

                // Ajustar el tamaño de cada botón para que sea cuadrado
                for (Button button : buttons) {
                    GridLayout.LayoutParams params = (GridLayout.LayoutParams) button.getLayoutParams();
                    params.width = buttonSize;  // Ancho del boton
                    params.height = buttonSize; // Alto del boton igual al ancho para que sea cuadrado
                    button.setLayoutParams(params);
                }

                // Remover el listener después de ajustar el tamaño
                glButtons.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void createButtons(GridLayout glButtons, int rowCount, int columnCount) {
        glButtons.setRowCount(rowCount);
        glButtons.setColumnCount(columnCount);
        buttons = new Button[rowCount * columnCount];

        for (int i = 0; i < buttons.length; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(i + 1));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            button.setLayoutParams(params);
            glButtons.addView(button);

            buttons[i] = button;
        }
    }
}