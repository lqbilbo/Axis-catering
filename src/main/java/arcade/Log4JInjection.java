/**
 * Copyright (C) 2009 Google Inc.
 *
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


package arcade;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.log4j.Logger;

public class Log4JInjection {

  @InjectLogger Logger logger;


  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new Log4JModule());

    Log4JInjection instance = injector.getInstance(Log4JInjection.class);
  }

}
