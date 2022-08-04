package com.example.dimensionamiento;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.dimensionamiento.databinding.FragmentSecondBinding;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class SecondFragment extends Fragment {
    private EditText editTextNumber,editTextSave,editTextHrSun,editTextPay3,editTextPay,editTextViewKwconsupcion;
    private TextView textViewRArea,textViewRKwI,textViewRNrPaneles,textViewRTotalCost,textViewNewRInfoInverters;
    private double valKw,valPorcentSave,valHrSun,valPay3, valPincrement, valPowerpanel, valFs,valKwcomsupcion,valRArea,valRKwI,valRNrPaneles,valRTotalCost;
    private Spinner  spinnerHrsSun,spinnerPIncrement,spinnerPowerPanel,spinnerFs;
    private String pro;
    private FragmentSecondBinding binding;
    private Object DecimalFormat;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);*/
                 valKw          = Long.parseLong(editTextNumber.getText().toString());
                 valPorcentSave = Long.parseLong(editTextSave.getText().toString());
                 valPay3        = Double.parseDouble(editTextPay3.getText().toString());
                 valHrSun       = Double.parseDouble(spinnerHrsSun.getSelectedItem().toString());
                 valPincrement  = Double.parseDouble(spinnerPIncrement.getSelectedItem().toString());
                 valPowerpanel  = Double.parseDouble(spinnerPowerPanel.getSelectedItem().toString());
                 valFs          = Double.parseDouble(spinnerFs.getSelectedItem().toString());
                 valKwcomsupcion= Double.parseDouble(editTextViewKwconsupcion.getText().toString());
                Calculate(valKw,valPorcentSave,valPay3,valKwcomsupcion,valHrSun,valPincrement,valPowerpanel,valFs);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void initViews(){
        editTextNumber = (EditText) getView().findViewById(R.id.editTextNumber);
        editTextPay3 = (EditText) getView().findViewById(R.id.editTextPay3);
        editTextSave = (EditText) getView().findViewById(R.id.editTextSave);
        editTextViewKwconsupcion= (EditText) getView().findViewById(R.id.editTextKwConsu);

        textViewRArea=(TextView) getView().findViewById(R.id.textViewRArea);
        textViewRKwI=(TextView) getView().findViewById(R.id.textViewRKwI);
        textViewRNrPaneles=(TextView) getView().findViewById(R.id.textViewRNrPaneles);
        textViewRTotalCost=(TextView) getView().findViewById(R.id.textViewRTotalCost);
        textViewNewRInfoInverters=(TextView) getView().findViewById(R.id.textViewNewRInfoInverters);

        spinnerHrsSun= (Spinner) getView().findViewById(R.id.spinnerHrSun);
        ArrayAdapter<CharSequence> adapterHrsSun = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.array_hrsSun, android.R.layout.simple_spinner_item);
        adapterHrsSun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHrsSun.setAdapter(adapterHrsSun);

        spinnerPIncrement = (Spinner) getView().findViewById(R.id.spinnerPIncrement);
        ArrayAdapter<CharSequence> adapterPIncrement = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.array_rate_increment, android.R.layout.simple_spinner_item);
        adapterPIncrement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPIncrement.setAdapter(adapterPIncrement);

        spinnerPowerPanel = (Spinner) getView().findViewById(R.id.spinnerPowerPanels);
        ArrayAdapter<CharSequence> adapterPowerSun  = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.array_powerSolarPanel, android.R.layout.simple_spinner_item);
        adapterPowerSun .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPowerPanel.setAdapter(adapterPowerSun);


        spinnerFs       =(Spinner) getView().findViewById(R.id.spinnerSF);
        ArrayAdapter<CharSequence> adapterFs = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.array_Fs, android.R.layout.simple_spinner_item);
        adapterFs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFs.setAdapter(adapterFs);


    }



    public void Calculate(double valKwT, double valPorcentSave, double valPay3, double valKwcomsup, double valHrSunT, double valPincrement, double valPowerpanel, double valFs)
    {

        double powerDay= (valKwT*1000)/29;
        double pIncrement= (1+(valPincrement/100));
        double pFs= (1+(valFs/100));
        double powerInstaller= ((powerDay/valHrSunT)*pIncrement*pFs)/1000;
        double nroPanels = powerInstaller/(valPowerpanel/1000);
        double Area = nroPanels*2;
        double valueKw=valPay3/valKwcomsup;
        double totalCost= valPorcentSave*powerInstaller;
        double retorno= (totalCost/(valueKw*valKwT))/12;


        DecimalFormatSymbols separador = new DecimalFormatSymbols();
        DecimalFormat format = new DecimalFormat("#.00",separador);  // crea formato para reducir numero de decimales

        textViewRNrPaneles.setText(format.format(nroPanels));
        textViewRArea.setText(format.format(Area));
        textViewRKwI.setText(format.format(powerInstaller));
        textViewRTotalCost.setText(format.format(totalCost));
        textViewNewRInfoInverters.setText(format.format(retorno));



    }

}
