package api.giybat.uz.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<S> {

    private boolean success;      // Operatsiya muvaffaqiyatlimi?
    private String message;       // Foydalanuvchiga xabar
    private Object data;               // Natija (optional)
    private LocalDateTime timestamp; // Javob vaqti

    // Faqat message qaytarish uchun konstruktor
    public AppResponse(String message) {
        this.success = true;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Faqat data qaytarish uchun konstruktor
    public AppResponse(Object data) {
        this.success = true;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
