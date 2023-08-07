package com.pymybatis.config.spy;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class SingleLineFormat implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return String.format("%s-%s | %s | %sms", category, connectionId, P6Util.singleLine(sql), elapsed);
    }
}
