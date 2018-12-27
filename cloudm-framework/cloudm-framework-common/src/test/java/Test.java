import com.google.common.base.Function;

import java.util.Map;

/**
 * @description:
 * @author: Courser
 * @date: 2017/8/1
 * @version: V1.0
 */
public enum Test implements Function<Map.Entry<?, ?>, Object> {
    KEY {
        @Override
        public Object apply(Map.Entry<?, ?> entry) {
            return entry.getKey();
        }
    },
    VALUE {
        @Override
        public Object apply(Map.Entry<?, ?> entry) {
            return entry.getValue();
        }
    };
}
