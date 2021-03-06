// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.googlesource.gerrit.plugins.cookbook;

import com.google.gerrit.reviewdb.client.Change;
import com.google.gerrit.server.query.OperatorPredicate;
import com.google.gerrit.server.query.Predicate;
import com.google.gerrit.server.query.QueryParseException;
import com.google.gerrit.server.query.change.ChangeData;
import com.google.gerrit.server.query.change.ChangeQueryBuilder;
import com.google.inject.Singleton;

@Singleton
public class SampleOperator
    implements ChangeQueryBuilder.ChangeOperatorFactory {
  public static class MyPredicate extends OperatorPredicate<ChangeData> {
    private final Change.Id id;

    MyPredicate(Change.Id id) {
      super("sample", id.toString());
      this.id = id;
    }

    @Override
    public boolean match(ChangeData object) {
      return id.equals(object.getId());
    }

    @Override
    public int getCost() {
      return 1;
    }
  }

  @Override
  public Predicate<ChangeData> create(ChangeQueryBuilder builder, String value)
      throws QueryParseException {
    return new MyPredicate(Change.Id.parse(value));
  }
}
