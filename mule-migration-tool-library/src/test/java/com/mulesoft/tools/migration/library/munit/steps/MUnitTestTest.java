/*
 * Copyright (c) 2020 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master Subscription
 * Agreement (or other master license agreement) separately entered into in writing between
 * you and MuleSoft. If such an agreement is not in place, you may not use the software.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.mulesoft.tools.migration.library.munit.steps;

import static com.mulesoft.tools.migration.helper.DocumentHelper.getDocument;
import static com.mulesoft.tools.migration.helper.DocumentHelper.getElementsFromDocument;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.mulesoft.tools.migration.exception.MigrationStepException;
import com.mulesoft.tools.migration.tck.ReportVerification;

import org.jdom2.Document;
import org.jdom2.Element;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MUnitTestTest {

  private static final String MUNIT_SAMPLE_XML = "munit-move-processors.xml";
  private static final Path MUNIT_EXAMPLES_PATH = Paths.get("munit/examples");
  private static final Path MUNIT_SAMPLE_PATH = MUNIT_EXAMPLES_PATH.resolve(MUNIT_SAMPLE_XML);

  @Rule
  public ReportVerification report = new ReportVerification();

  private MUnitTest munitTest;
  private Element node;

  @Before
  public void setUp() throws Exception {
    munitTest = new MUnitTest();
  }

  @Test(expected = MigrationStepException.class)
  public void executeWithNullElement() throws Exception {
    munitTest.execute(null, report.getReport());
  }

  @Test
  public void validateTestStructure() throws Exception {
    Document doc = getDocument(this.getClass().getClassLoader().getResource(MUNIT_SAMPLE_PATH.toString()).toURI().getPath());
    node = getElementsFromDocument(doc, munitTest.getAppliedTo().getExpression()).get(0);
    munitTest.execute(node, report.getReport());

    assertThat("The munit test not contains sections.", node.getChildren().size(), is(3));
    assertThat("The munit test not contains sections.", node.getChildren().get(0).getName(), is("behavior"));
    assertThat("The munit test not contains sections.", node.getChildren().get(1).getName(), is("execution"));
    assertThat("The munit test not contains sections.", node.getChildren().get(2).getName(), is("validation"));
  }
}