package socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Message {
    private Integer id;
    private String from;
    private List<String> to;
    private String text;
    private LocalDateTime data;
    private Integer reply;

    public Message(Integer id, String from, List<String> to,String text, LocalDateTime data, Integer reply) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.text = text;
        this.data = data;
        this.reply = reply;
    }

    public Integer getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Integer getReply() {
        return reply;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }
}


