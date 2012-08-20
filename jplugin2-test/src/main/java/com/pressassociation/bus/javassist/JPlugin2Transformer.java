/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pressassociation.bus.javassist;

import javassist.*;

import java.io.File;

public class JPlugin2Transformer {

    private final ClassPool pool = ClassPool.getDefault();

    private static class InstanceHolder {
        private static final JPlugin2Transformer instance = new JPlugin2Transformer();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected argument: directory with classes to transform");
        }
        File classesDir = new File(args[0]);

        if (!classesDir.isDirectory()) {
            throw new IllegalArgumentException("Expected argument to be a directory with classes to transform");
        }

        InstanceHolder.instance.instrumentClassesIn(classesDir);

        System.out.println(">>>" + JPlugin2Transformer.class.getSimpleName() + ": Transformation done for "
                + classesDir.getAbsolutePath());
    }

    private void instrumentClassesIn(File classesDir) throws Exception {
        pool.appendClassPath(classesDir.getPath());

        String[] targetClasses = new String[]{"com.ibm.broker.plugin.MbMessage", "com.ibm.broker.plugin.MbElement"};

        for (String target : targetClasses) {
            instrument(target).writeFile(classesDir.getPath());
        }
    }

    private CtClass instrument(String targetClass) throws NotFoundException, CannotCompileException {
        final CtClass nodeClass = pool.get(targetClass);
        CtMethod hashCodeMethod = CtNewMethod.make("public int hashCode() { return (int) handle_; }", nodeClass);
        nodeClass.addMethod(hashCodeMethod);
        String method =
                "public boolean equals(java.lang.Object o) { " +
                        "return o instanceof " + targetClass + " && ((" + targetClass + ")o).handle_ == this.handle_; " +
                "}";
        System.out.println(method);
        CtMethod equalsMethod = CtNewMethod.make(method, nodeClass);
        nodeClass.addMethod(equalsMethod);
        return nodeClass;
    }

}