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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;
import com.sun.syndication.feed.synd.SyndEntry;

@Entity
@Table(name = "RELOCKED_TITLE")
@NamedQueries({//
@NamedQuery(name = "findLastRelockedTitlesOrderByDateDesc", query = "SELECT rt FROM RelockedTitle rt ORDER BY rt.relockDate DESC") //
})
public class RelockedTitle implements Rssable {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Player player;

	@ManyToOne
	private Title title;

	@Column(name = "RELOCK_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date relockDate;

	@ManyToOne
	private Score relockerScore;

	public RelockedTitle() {

	}

	@PrePersist
	public void prePersist() {
		relockDate = new Date();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RelockedTitle) {
			RelockedTitle u = (RelockedTitle) obj;
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

	public Score getRelockerScore() {
		return relockerScore;
	}

	public void setRelockerScore(Score relockerScore) {
		this.relockerScore = relockerScore;
	}

	public void setRelockDate(Date relockDate) {
		this.relockDate = relockDate;
	}

	public Date getRelockDate() {
		return relockDate;
	}

	@Override
	public SyndEntry asEntry() {
		String titleLabel = getTitle().getLabel();
		String playerName = getPlayer().getFullname();
		String title = playerName;
		title += " lost the title " + titleLabel;
		String link = "http://www.neogeo-hiscores.com/faces/player/view.xhtml?fullname=" + playerName;
		Date date = getRelockDate();
		return Entries.createEntry(title, link, date);
	}

}
