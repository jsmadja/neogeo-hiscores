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

import static com.google.common.base.Objects.equal;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;

@Entity
@Table(name = "UNLOCKED_TITLE")
@NamedQueries({//
@NamedQuery(name = "findLastUnlockedTitlesOrderByDateDesc", query = "SELECT ut FROM UnlockedTitle ut ORDER BY ut.unlockDate DESC") //
})
public class UnlockedTitle {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Player player;

	@ManyToOne
	private Title title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date unlockDate;

	public UnlockedTitle() {

	}

	public UnlockedTitle(Player player, Title title) {
		this.player = player;
		this.title = title;
		this.unlockDate = new Date();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UnlockedTitle) {
			UnlockedTitle u = (UnlockedTitle) obj;
			return equal(player, u.player) && equal(title, u.title);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(player, title);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Date getUnlockDate() {
		return unlockDate;
	}

	public void setUnlockDate(Date unlockDate) {
		this.unlockDate = unlockDate;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).//
				add("Title", title).//
				add("Player", player).//
				add("Unlock date", unlockDate).//
				toString();
	}

}
