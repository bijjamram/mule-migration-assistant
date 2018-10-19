/*
 * Copyright (c) 2017 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master Subscription
 * Agreement (or other master license agreement) separately entered into in writing between
 * you and MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.tools.migration.library.mule.steps.email;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.jdom2.Namespace.getNamespace;

import com.mulesoft.tools.migration.project.model.ApplicationModel;
import com.mulesoft.tools.migration.step.AbstractApplicationModelMigrationStep;

import org.jdom2.Element;
import org.jdom2.Namespace;

import java.util.Optional;

/**
 * Support for migrating elements of the email connector
 *
 * @author Mulesoft Inc.
 * @since 1.0.0
 */
public abstract class AbstractEmailMigrator extends AbstractApplicationModelMigrationStep {

  public static final Namespace EMAIL_NAMESPACE = getNamespace("email", "http://www.mulesoft.org/schema/mule/email");
  public static final String EMAIL_SCHEMA_LOC = "http://www.mulesoft.org/schema/mule/email/current/mule-email.xsd";

  public AbstractEmailMigrator() {
    this.setNamespacesContributions(asList(EMAIL_NAMESPACE,
                                           getNamespace("smtp", "http://www.mulesoft.org/schema/mule/smtp"),
                                           getNamespace("smtps", "http://www.mulesoft.org/schema/mule/smtps"),
                                           getNamespace("imap", "http://www.mulesoft.org/schema/mule/imap"),
                                           getNamespace("imaps", "http://www.mulesoft.org/schema/mule/imaps"),
                                           getNamespace("pop3", "http://www.mulesoft.org/schema/mule/pop3"),
                                           getNamespace("pop3s", "http://www.mulesoft.org/schema/mule/pop3s")));
  }

  protected Optional<Element> resolveConnector(Element object, ApplicationModel appModel) {
    Optional<Element> connector;
    if (object.getAttribute("connector-ref") != null) {
      connector = of(getConnector(object.getAttributeValue("connector-ref")));
      object.removeAttribute("connector-ref");
    } else {
      connector = getDefaultConnector();
    }
    return connector;
  }

  protected abstract Element getConnector(String connectorName);

  protected abstract Optional<Element> getDefaultConnector();

}