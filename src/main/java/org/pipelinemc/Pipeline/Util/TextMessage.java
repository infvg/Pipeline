package org.pipelinemc.Pipeline.Util;

import org.apache.commons.lang.Validate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class TextMessage {

    /**
     * The formatting ChatColors.
     */
    private static final ChatColor[] FORMATTING = {
            ChatColor.MAGIC, ChatColor.BOLD, ChatColor.STRIKETHROUGH, ChatColor.UNDERLINE, ChatColor.ITALIC
    };

    /**
     * The JSON structure of this text message.
     */
    private final JSONObject object;

    public TextMessage(String text) {
        object = convert(text);
    }

    public TextMessage(JSONObject object) {
        Validate.notNull(object, "object must not be null");
        this.object = object;
    }

    public String encode() {
        return object.toJSONString();
    }

    public String asPlaintext() {
        if (object.containsKey("text")) {
            Object obj = object.get("text");
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        return "";
    }

    public String flatten() {
        StringBuilder builder = new StringBuilder();
        flatten(builder, object);
        return builder.toString();
    }

    private static void flatten(StringBuilder dest, JSONObject obj) {
        if (obj.containsKey("color")) {
            try {
                dest.append(ChatColor.valueOf(obj.get("color").toString().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                // invalid color, ignore
            }
        }
        for (ChatColor format : FORMATTING) {
            String name = format == ChatColor.MAGIC ? "obfuscated" : format.name().toLowerCase();
            if (obj.containsKey(name) && obj.get(name).equals(true)) {
                dest.append(format);
            }
        }
        if (obj.containsKey("text")) {
            dest.append(obj.get("text").toString());
        }
        if (obj.containsKey("extra")) {
            JSONArray array = (JSONArray) obj.get("extra");
            for (Object o : array) {
                if (o instanceof JSONObject) {
                    flatten(dest, (JSONObject) o);
                } else {
                    dest.append(o);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Message" + encode();
    }

    public static TextMessage decode(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object o = parser.parse(json);
            if (o instanceof JSONObject) {
                return new TextMessage((JSONObject) o);
            } else {
                return new TextMessage(o.toString());
            }
        } catch (ParseException e) {
            return new TextMessage(json);
        }
    }

    @SuppressWarnings("unchecked")
    private static JSONObject convert(String text) {
        // state
        final List<JSONObject> items = new LinkedList<>();
        final Set<ChatColor> formatting = EnumSet.noneOf(ChatColor.class);
        final StringBuilder current = new StringBuilder();
        ChatColor color = null;

        // work way through text, converting colors
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch != ChatColor.COLOR_CHAR) {
                // no special handling
                current.append(ch);
                continue;
            }

            if (i == text.length() - 1) {
                // ignore color character at end
                continue;
            }

            // handle colors
            append(items, current, color, formatting);
            ChatColor code = ChatColor.getByChar(text.charAt(++i));
            if (code == ChatColor.RESET) {
                color = null;
                formatting.clear();
            } else if (code.isFormat()) {
                formatting.add(code);
            } else {
                color = code;
                formatting.clear();
            }
        }
        append(items, current, color, formatting);

        // convert list of items into structure
        if (items.isEmpty()) {
            // no items, return a blank message
            JSONObject object = new JSONObject();
            object.put("text", "");
            return object;
        } else if (items.size() == 1) {
            // only one item, return it as-is
            return items.get(0);
        } else {
            JSONObject object = items.get(0);
            if (object.size() == 1) {
                // only contains "text", no formatting, can reuse
                object.put("extra", items.subList(1, items.size()));
            } else {
                // must put everything in the "extra" list
                object = new JSONObject();
                object.put("text", "");
                object.put("extra", items);
            }
            return object;
        }
    }

    @SuppressWarnings("unchecked")
    private static void append(List<JSONObject> items, StringBuilder current, ChatColor color, Set<ChatColor> formatting) {
        if (current.length() == 0) {
            return;
        }

        JSONObject object = new JSONObject();
        object.put("text", current.toString());
        if (color != null) {
            object.put("color", color.name().toLowerCase());
        }
        for (ChatColor format : formatting) {
            if (format == ChatColor.MAGIC) {
                object.put("obfuscated", true);
            } else {
                object.put(format.name().toLowerCase(), true);
            }
        }
        current.setLength(0);
        items.add(object);
    }

}
