/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.stringsubstitutor;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.text.StringSubstitutor;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestStringSubstitutorTemplateEngine {
    @Mock
    private StatementContext ctx;

    private final Map<String, Object> attributes = new HashMap<>();

    @Before
    public void before() {
        when(ctx.getAttributes()).thenReturn(attributes);
    }

    @Test
    public void testDefaults() {
        attributes.put("name", "foo");

        assertThat(StringSubstitutorTemplateEngine.DEFAULTS.render("create table ${name};", ctx))
            .isEqualTo("create table foo;");
    }

    @Test
    public void testCustomPrefixSuffix() {
        attributes.put("name", "foo");

        StringSubstitutorTemplateEngine engine = (StringSubstitutor substitutor) -> {
            substitutor.setVariablePrefix('<');
            substitutor.setVariableSuffix('>');
        };

        assertThat(engine.render("create table <name>;", ctx))
            .isEqualTo("create table foo;");
    }
}
