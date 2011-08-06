/**
 *     Copyright (C) 2011 Julien SMADJA <julien dot smadja at gmail dot com>
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.anzymus.neogeo.hiscores.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.google.common.base.Objects;
import java.io.Serializable;

@Entity
@Table(name = "TITLE")
public class Title implements Serializable, Comparable<Title> {

    @Id
    @GeneratedValue
    private Long id;

    private String label;

    private String description;

    private String classname;

    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
    private Set<UnlockedTitle> unlockedTitles = new HashSet<UnlockedTitle>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UnlockedTitle> getUnlockedTitles() {
        return unlockedTitles;
    }

    public void setUnlockedTitles(Set<UnlockedTitle> unlockedTitles) {
        this.unlockedTitles = unlockedTitles;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this) //
                .add("label", label) //
                .add("description", description) //
                .add("classname", classname) //
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Title) {
            Title title = (Title) obj;
            return Objects.equal(id, title.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(label, description);
    }

    @Override
    public int compareTo(Title t) {
        return label.compareTo(t.label);
    }
}
