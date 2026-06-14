package br.com.oboticariorevenda.oboticario_revenda.model;

import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SocialAnalytics {
    private AtomicLong instagram_clicks = new AtomicLong(0);
    private AtomicLong whatsapp_clicks = new AtomicLong(0);

    public void instagramClicksAdd() {
        instagram_clicks.getAndIncrement();
    }

    public void whatsappClicksAdd() {
        whatsapp_clicks.getAndIncrement();
    }
}
