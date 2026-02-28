package com.taskmanagement.taskmanagement.Entity;

import com.taskmanagement.taskmanagement.Enums.IssueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "board_column",indexes = {@Index(columnList=("board_id,position"))})
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String name;

    private IssueStatus statusKey;

    private Integer position;

    private Integer wipLimit;
}
