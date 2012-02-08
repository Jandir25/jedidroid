package org.sr.projecte;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CalculadoraActivity extends Activity
{
	
	protected static final String LOG = "Calculadora";
	private String sRes = ""; // "" => Encara no hem introduit el numero 1
	private String sRes2 = ""; // "" => Encara no hem introduit el numero 2
	private String op = ""; // Operacio. "" => encara no s'ha introduit cap
	private int twop = 0; // Dos vegades el simbol - indica que el numero sera negatiu. Cal controlar-ho.
	private boolean coma1 = false; // Coma flotant al numero 1
	private boolean coma2 = false; // Coma flotant al numero 2
	private boolean crash = false; // Si es divideix per 0
	private int num; // El numero indica quin sa apretat
	private boolean negatiu = false; // per saber signe;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        
    	/* Enable full screen mode */
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.calculadora);
            
        
        // Declarem el edit text
        EditText etRes = (EditText) findViewById(R.id.textRes);
        etRes.setCursorVisible(false);
        etRes.setText("");
	    // Declarem tots els buttons
	    Button bSuma = (Button) findViewById(R.id.suma);
		Button bResta = (Button) findViewById(R.id.resta);
		Button bMult = (Button) findViewById(R.id.mult);
		Button bDiv = (Button) findViewById(R.id.div);
		Button bOne = (Button) findViewById(R.id.one);
		Button bTwo = (Button) findViewById(R.id.two);
		Button bThree = (Button) findViewById(R.id.three);
		Button bFour = (Button) findViewById(R.id.Four);
		Button bFive = (Button) findViewById(R.id.Five);
		Button bSix = (Button) findViewById(R.id.Six);
		Button bSeven = (Button) findViewById(R.id.seven);
		Button bEight = (Button) findViewById(R.id.eight);
		Button bNine = (Button) findViewById(R.id.nine);
		Button bZero = (Button) findViewById(R.id.zero);
		Button bBack = (Button) findViewById(R.id.Back);
		Button bEqual = (Button) findViewById(R.id.calculate);
		Button bComa = (Button) findViewById(R.id.coma);
		Button bFact = (Button) findViewById(R.id.fact);
		// El nostre listener que implementa el de android
		MyListenerOnClick mLOC = new MyListenerOnClick();
		// Afegim el listener a tots els buttons
		bSuma.setOnClickListener(mLOC);
		bResta.setOnClickListener(mLOC);
		bMult.setOnClickListener(mLOC);
		bDiv.setOnClickListener(mLOC);
		bOne.setOnClickListener(mLOC);
		bTwo.setOnClickListener(mLOC);
		bThree.setOnClickListener(mLOC);
		bFour.setOnClickListener(mLOC);
		bFive.setOnClickListener(mLOC);
		bSix.setOnClickListener(mLOC);
		bSeven.setOnClickListener(mLOC);
		bEight.setOnClickListener(mLOC);
		bNine.setOnClickListener(mLOC);
		bZero.setOnClickListener(mLOC);
		bComa.setOnClickListener(mLOC); 
		bBack.setOnClickListener(mLOC);
		bEqual.setOnClickListener(mLOC);
		bFact.setOnClickListener(mLOC);
    }
    
    
    // Classe que gestiona els events de la calculadora
	class MyListenerOnClick implements OnClickListener 
	{
		// Funcio per calcular resultats
		private Double calcular_resultat(Double val1, Double val2, String ope)
		{
			// Permetem coma flotant
			coma2 = false;
		    // Primer de tot comprovar que no ens passen una divisio entre 0
			if (ope.equals("/") && val2 == 0)
			{
				sRes = "";
				sRes2 = "";
				op = "";
				crash = true; // Activem l'error
				return 0.;
			}
			// Efectuem operacions  suma-resta-mult-divisio
			if (ope.equals("+")) return val1+val2;
			else if (ope.equals("-")) return val1-val2;
			else if (ope.equals("*")) return val1*val2;
			else if (ope.equals("/" )) return val1/val2;
			else
			{
				return factorial(val1);
			}
		}
		
		// Funcio per concatenar numeros a les strings dels resultats
		private void concat_result(int num)
		{
			
			// Cal tenir en compte si estem introduint el primer terme 
			// o el segon (mirant que hi hagi o no op o no siguin buits)
			if (!op.equals("") && !sRes.equals("") && !op.equals("!")) // Segon terme sempre i quant s'hagi posat un primer 
				{
				if (twop >= 2 && op.equals("-") || negatiu) sRes2 = "-" + sRes2 + ""+ num;
				else sRes2 = sRes2 + ""+ num;
				}
				
			else if (op.equals(""))// Primer terme abans d'operacio
			{
				if (twop >= 2 && op.equals("-") || negatiu) sRes = "-" + sRes + ""+ num;
				else sRes = sRes + ""+ num;
			}
			negatiu = false;
			
		
				
		}
		
		// Funcio per fer el factorial
		private double factorial (double num)
		{
			if (num < 2) return 1;
			else 
			{
				double res = 1;
				while (num > 0) // Recursivitat en java = Suicidi. Don't try. Seriously
				{
					res *= num;
					--num;
				}
				return res;	
			}
		} 
		
		// Funcio per tractar la operacio introduida, tractarem la op i mantindrem el resultat
		// sempre i quan sigui possible seguir. 
		// Si hem apretat un -, cal tenir en compte si es per donar valor al nmero negatiu o no
		private void tractar_operacio(String ope)
		{
			if (ope.equals("-") && sRes.equals("")) // Cap numero introduit, introduim primer el simbol negatiu
			{
				
					negatiu = true; // Si el signe es negatiu
			}
			else if (ope.equals("-") && !op.equals("") && sRes2.equals("")) // El numero a introduir es el segon, control de signe
			{
				negatiu = true;
			}
			if (op.equals("") && !sRes.equals("")) // Demano una operacio per primer cop
				op = ope;
			else if (!sRes.equals("") && !negatiu) // Actualitzo resultat i permeto operar
			{
				if (twop == 0)
				{
					Double d;
					if (!op.equals("!")) // Si no es un factorial opero x op y
						d = calcular_resultat(Double.parseDouble(sRes),Double.parseDouble(sRes2),op);
					else  // Si es un factorial opero fact(x)
						d = calcular_resultat(Double.parseDouble(sRes),0.0,op);
					if (!crash) sRes = ""+d;
				}
				if (!crash) 
				{
					sRes2 = "";
					op = ope;	
				}	
			}
			twop++;		
		
		}
		
		
		@Override
		public void onClick(View v) 
		{
			EditText etRes = (EditText) findViewById(R.id.textRes);
			// Aconsegueixo la id del boto apretat
		
			
			switch(v.getId())
			{
				// Casos en que soc una operacio
				case R.id.suma:
					tractar_operacio("+");
					break;
					
				case R.id.resta:
					tractar_operacio("-");
					break;

					
				case R.id.mult:
					tractar_operacio("*");
					break;
					
				case R.id.div:
					tractar_operacio("/");
					break;
					
					
				case R.id.fact:
					if (op.equals("")  && !sRes.equals("")) op = "!"; // Demano una operacio per primer cop
					break;
				
				// Control de coma flotant
				case R.id.coma:
					if (!coma1 && op.equals("") && !sRes.equals("")) // NO he posat coma, hi ha almenys un num, estic int. 1r terme
					{
						sRes = sRes + ".";
						coma1 = true;
					}
					else if (!coma2 && !op.equals("") && !sRes2.equals("")) // NO he posat coma, hi ha almenys un num, estic int. 2nd terme
					{
						sRes2 = sRes2 + ".";
						coma2 = true;
					}
					break;
					
				case R.id.Back:
					op = "";
					sRes = "";
					sRes2 = "";
					etRes.setText("");
					coma1 = false;
					coma2 = false;
					break;
					
				// Control de numeros
				case R.id.zero:
					num = 0;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.one:
					num = 1;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.two:
					num = 2;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.three:
					num = 3;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.Four:
					num = 4;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.Five:
					num = 5;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.Six:
					num = 6;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.seven:
					num = 7;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.eight:
					num = 8;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.nine:
					num = 9;
					concat_result(num);
					twop = 0;
					break;
					
				case R.id.calculate:
					Double d;
					if (!sRes2.equals("") && !sRes.equals(""))
					{
						 d = calcular_resultat(Double.parseDouble(sRes),Double.parseDouble(sRes2),op);
						 etRes.setText(""+d);
					}
					else if (!sRes.equals(""))
					{
						if (op.equals("!"))
							d = calcular_resultat(Double.parseDouble(sRes),0.0,op);
						else 
							d = Double.parseDouble(sRes);
						etRes.setText(""+d);
					}
					
					sRes = "";
					sRes2 = "";
					op = "";
					coma1 = false;
					coma2 = false;
			    default:
			
			}
			// Control del display de text
			if(!crash && !sRes.equals("")) etRes.setText(sRes+op+sRes2); // Numeros en forma x op y
			else if (crash) etRes.setText("ERROR"); // Display de l'error
			crash = false;
			
		}


	}
}