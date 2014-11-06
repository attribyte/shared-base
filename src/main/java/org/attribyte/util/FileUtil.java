/*
 * Copyright 2011 Attribyte, LLC 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License.  
 * 
 */

package org.attribyte.util;

import java.io.File;
import java.io.IOException;


/**
 * Utility methods for files.
 * @author Matt Hamer - Attribyte, LLC
 */
public class FileUtil {

   /**
    * Attempt to determine if a file is a symlink.
    * @param file The file.
    * @return Is the file a symlink?
    * @throws java.io.IOException on filesystem error
    */
   public static final boolean isSymlink(final File file) throws IOException {
      return !(file.getAbsolutePath().equals(file.getCanonicalPath()));
   }
}