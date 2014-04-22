/*
 * Copyright 2012-2014, Continuuity, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.continuuity.loom.admin;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Machine providers are instances of some {@link ProviderType} like openstack, aws, rackspace, or joyent that can
 * provision machines.  Providers are referenced by {@link ImageType} and {@link HardwareType}.
 */
public final class Provider extends NamedEntity {
  private final String description;
  private final String providerType;
  private final Map<String, String> provisionerFields;

  public Provider(String name, String description, String providerType, Map<String, String> provisionerFields) {
    super(name);
    Preconditions.checkArgument(providerType != null, "invalid provider type.");
    this.description = description;
    this.providerType = providerType;
    this.provisionerFields = provisionerFields == null ? ImmutableMap.<String, String>of() : provisionerFields;
  }

  /**
   * Get the description of the provider.
   *
   * @return Description of provider.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get the type of the provider.
   *
   * @return Type of the provider.
   */
  public String getProviderType() {
    return providerType;
  }

  /**
   * Get fields needed by provisioners for this provider.  This should include fields defined in the corresponding
   * {@link ProviderType}.
   *
   * @return Fields needed to provision machines from this provider.
   */
  public Map<String, String> getProvisionerFields() {
    return provisionerFields;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Provider)) {
      return false;
    }
    Provider other = (Provider) o;
    return Objects.equal(name, other.name) &&
      Objects.equal(description, other.description) &&
      Objects.equal(providerType, other.providerType) &&
      Objects.equal(provisionerFields, other.provisionerFields);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, description, providerType, provisionerFields);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
      .add("description", description)
      .add("providerType", providerType)
      .add("provisionerFields", provisionerFields)
      .toString();
  }
}
