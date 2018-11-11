package negocio;

import java.text.ParseException;
import javafx.scene.control.DatePicker;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Marcos Ricardo Rodrigues
 */
public class DatePickerFormatter {

    private final MaskFormatter mf;
    private DatePicker dp;
    private String CaracteresValidos;
    private String mask;

    public DatePickerFormatter() {
        mf = new MaskFormatter();
    }

    public void formatter(DatePicker dp, String CaracteresValidos, String mask) {
        try {
            mf.setMask(mask);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

        mf.setValidCharacters(CaracteresValidos);
        mf.setValueContainsLiteralCharacters(false);
        String text = dp.getEditor().getText().replaceAll("[\\W]", "");
        
        boolean repetir = true;
        while (repetir) {

            char ultimoCaractere;

            if (text.equals("")) {
                break;
            } else {
                ultimoCaractere = text.charAt(text.length() - 1);
            }

            try {
                text = mf.valueToString(text);
                repetir = false;
            } catch (ParseException ex) {
                text = text.replace(ultimoCaractere + "", "");
                repetir = true;
            }

        }

        dp.getEditor().setText(text);

        if (!text.equals("")) {
            for (int i = 0; dp.getEditor().getText().charAt(i) != ' ' && i < dp.getEditor().getLength() - 1; i++) {
                dp.getEditor().forward();
            }
        }
    }

    public void formatter(){
        formatter(this.dp, this.CaracteresValidos, this.mask);
    }

    /**
     * @return the dp
     */
    public DatePicker getDp() {
        return dp;
    }

    /**
     * @param dp the dp to set
     */
    public void setDp(DatePicker dp) {
        this.dp = dp;
    }

    /**
     * @return the CaracteresValidos
     */
    public String getCaracteresValidos() {
        return CaracteresValidos;
    }

    /**
     * @param CaracteresValidos the CaracteresValidos to set
     */
    public void setCaracteresValidos(String CaracteresValidos) {
        this.CaracteresValidos = CaracteresValidos;
    }
    
    public String getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(String mask) {
        this.mask = mask;
    }
}