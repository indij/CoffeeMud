package com.planet_ink.coffee_mud.system.intermud.packets;

/**
 * Copyright (c) 1996 George Reese
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
public class I3Exception extends Exception 
{
	public static final long serialVersionUID=0;
    public I3Exception() 
    {
        this("Unidentified exception.");
    }

    public I3Exception(String str) 
    {
        super(str);
    }
}
